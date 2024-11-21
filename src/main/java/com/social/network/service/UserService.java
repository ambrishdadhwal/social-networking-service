package com.social.network.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.social.network.entity.UserProfileE;
import com.social.network.notification.EmailDetailDTO;
import com.social.network.presentation.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.social.network.utils.ProfileMapper;
import com.social.network.utils.SocialMethodVisit;
import com.social.network.domain.UserProfile;
import com.social.network.repository.ProfileRepo;
import com.social.network.repository.UserRepo;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

@Service("profileService")
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService
{

	private final UserRepo userRepo;

	private final ProfileRepo profileRepo;

	private final RabbitTemplate rabbitTemplate;

	private final IPostService postService;

	@Value("${rabbitmq.exchange.email.name}")
	private String emailExchange;

	@Value("${rabbitmq.binding.email.name}")
	private String emailRoutingKey;

	@Value("${spring.rabbitmq.enabled:false}")
	private String isRabbitMQEnabled;

	@SocialMethodVisit
	public Optional<UserProfile> saveUser(UserProfile user) throws Exception
	{
		Optional<UserProfileE> existingUser = userRepo.findByEmail(user.getEmail());
		if (existingUser.isPresent())
		{
			throw new ProfileException("User already exist with email -- " + user.getEmail());
		}

		UserProfileE newUser = ProfileMapper.convert(user);
		newUser.setCreateDateTime(LocalDateTime.now());
		newUser.setModifiedDateTime(LocalDateTime.now());
		newUser.setIsActive(true);
		try
		{
			Integer token = Integer.valueOf((int) (Math.random() * 10000));
			UserProfileE savedUser = userRepo.save(newUser);
			sendEmail(user, token, "Email Verification");
			return Optional.of(ProfileMapper.convert(savedUser));
		}
		catch (Exception e)
		{
			throw new ProfileException(e.getLocalizedMessage());
		}
	}

	@SocialMethodVisit
	public List<UserProfile> allUsers()
	{
		return userRepo.findAll().stream().map(ProfileMapper::convert).collect(Collectors.toList());
	}
	
	@Override
	@SocialMethodVisit
	@Cacheable(cacheNames = {"personCache"}, key = "'persons_all_'+#pageNumber +'_'+#pageSize")
	public List<UserProfile> allUsersPaging(Integer pageNumber, Integer pageSize)
	{
		log.info("Getting all the users.......");
		Sort sortByName = Sort.by("firstName","lastName");
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByName);
		return userRepo.findAll(pageable).getContent().stream().map(ProfileMapper::convert).collect(Collectors.toList());
		//return userRepo.findAll().stream().map(ProfileMapper::convert).collect(Collectors.toList());
	}

	@SocialMethodVisit
	public Optional<UserProfile> getUserbyId(Long userId)
	{
		return allUsers().stream().filter(n -> n.getId().equals(userId)).findAny();
	}

	@Override
	@SocialMethodVisit
	public Optional<UserProfile> getUserbyUserNameAndId(@NotEmpty String userName, Long userId)
	{
		return allUsers().stream()
			.filter(n -> n.getEmail().equals(userName) && n.getId().equals(userId))
			.findAny();
	}

	@Override
	@SocialMethodVisit
	public Optional<UserProfile> deleteUserById(Long userId) throws Exception {
		CommonResponse<Boolean> response = postService.deleteAllUserPost(userId);
		if (response.getError() != null){
			throw new Exception(response.getError());
		}
		Optional<UserProfileE> user = userRepo.findById(userId);
		userRepo.deleteById(userId);
		log.info("User succesfully deleted - {} " , user.get().getEmail());
		return Optional.ofNullable(ProfileMapper.convert(user.get()));
	}

	public long totalSocialUsers()
	{
		return profileRepo.totalUsers();
	}

	@Override
	public Optional<UserProfile> getUser(UserProfile user) throws Exception
	{
		return profileRepo.getUser(user);
	}

	@Override
	@SocialMethodVisit
	public Optional<UserProfile> updateUser(UserProfile user)
	{
		Optional<UserProfileE> existingDBUser = userRepo.findById(user.getId());

		if (existingDBUser.isPresent())
		{
			UserProfileE profile = existingDBUser.get();
			profile.setFirstName(user.getFirstName());
			profile.setLastName(user.getLastName());
			profile.setCountry(user.getCountry());
			profile.setDob(user.getDob());
			profile.setModifiedDateTime(LocalDateTime.now());
			UserProfileE savedUser = userRepo.save(profile);
			return Optional.of(ProfileMapper.convert(savedUser));
		}
		return Optional.empty();
	}

	@Override
	public Optional<UserProfile> getUserbyUserName(String userName)
	{
		return allUsers().stream().filter(n -> n.getUserName().equals(userName)).findAny();
	}

	@Override
	public Optional<UserProfile> getUserbyEmail(String email)
	{
		Optional<UserProfileE> existingUser = userRepo.findByEmail(email);
		return Optional.of(ProfileMapper.convert(existingUser.get()));
	}

	@Override
	public List<UserProfile> getUserbyuserEmail(String email)
	{
		return allUsers().stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
	}

	@Override
	public List<UserProfile> searchUsers(String search)
	{
		return getUsersSearchbyName(search);
	}

	@Override
	@SocialMethodVisit
	public List<UserProfile> getUsersSearchbyName(String name)
	{
		return userRepo.findByFirstNameContaining(name).stream().map(ProfileMapper::convert).collect(Collectors.toList());
	}

	private void sendEmail(UserProfile user, Integer token, String subject) {
		if(isRabbitMQEnabled!=null && isRabbitMQEnabled.equals("true")){
			Map<String, Object> mailData = Map.of("token", token, "fullName", user.getFirstName().concat(user.getLastName()));
			rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, EmailDetailDTO.builder()
					.to(user.getEmail())
					.subject(subject)
					.dynamicValue(mailData)
					.templateName("verification")
					.build());
		}
		else{
			log.info("RabbitMQ push currently disabled.");
		}
	}

}

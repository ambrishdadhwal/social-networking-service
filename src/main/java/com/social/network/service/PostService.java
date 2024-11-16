package com.social.network.service;

		import java.util.List;
		import java.util.Optional;

		import com.social.network.presentation.CommonResponse;
		import com.social.network.security.RequestContextHolder;
		import org.springframework.http.HttpStatus;
		import org.springframework.stereotype.Service;

		import com.social.network.utils.UserPostMapper;
		import com.social.network.domain.UserPost;
		import com.social.network.entity.ProfileE;
		import com.social.network.entity.UserPostE;
		import com.social.network.repository.ProfileImageRepo;
		import com.social.network.repository.UserPostRepo;
		import com.social.network.repository.UserRepo;

		import lombok.RequiredArgsConstructor;
		import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements IPostService
{

	final UserPostRepo postRepo;

	final UserRepo userRepo;

	final ProfileImageRepo imageRepo;

	private final RequestContextHolder requestContextHolder;

	@Override
	public Optional<UserPost> addUserPost(UserPost profilePost)
	{
		UserPostE profilePostE = UserPostMapper.convertEntity(profilePost);

		Optional<ProfileE> profile = userRepo.findById(profilePost.getUserId());
		profilePostE.getImages().forEach(m -> {
			m.setUser(profile.get());
		});

		Optional<UserPost> result = Optional.empty();
		if (profile.isPresent())
		{
			profilePostE.setUser(profile.get());
			profilePostE = postRepo.save(profilePostE);
			result = Optional.of(UserPostMapper.convert(profilePostE));
		}
		return result;
	}

	@Override
	public List<UserPost> getAllUserPost(Long userId)
	{
		return postRepo.getPostsByProfile(userId).stream().map(UserPostMapper::convert).toList();
	}

	@Override
	public UserPost getUserPostByUserIdAndPostId(Long userId, Long postId)
	{
		return UserPostMapper.convert(postRepo.getPostsByUserIdAndPostId(userId, postId));
	}

	@Override
	public CommonResponse<Boolean> deleteAllUserPost(Long userId)
	{

		CommonResponse response =  CommonResponse
				.builder()
				.status(HttpStatus.OK)
				.build();

		long loggedInUser = requestContextHolder.getContext().getUserId();
		if (loggedInUser != userId){
			response.setStatus(HttpStatus.FORBIDDEN);
			response.setError("You must logged with required user.");
			return response;
		}

		try{
			imageRepo.deleteAllByUserId(userId);

			postRepo.deleteByUserId(userId);

			log.info("Successfully deleted user post for userId {} ", userId);
			response.setData(true);
		}
		catch (Exception e) {
			response.setError(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public CommonResponse<Boolean> deleteUserPost(Long userId, Long postId)
	{
		CommonResponse response =  CommonResponse
				.builder()
				.status(HttpStatus.OK)
				.build();

		long loggedInUser = requestContextHolder.getContext().getUserId();
		if (loggedInUser != userId){
			response.setStatus(HttpStatus.FORBIDDEN);
			response.setError("You must logged with required user.");
			return response;
		}

		try{
			imageRepo.deleteAllByUserIdAndPostId(userId, postId);

			postRepo.deleteByPostIdAndUserId(userId, postId);

			log.info("Successfully deleted user post for userId {} and postId {}", userId, postId);
			response.setData(true);
		}
		catch (Exception e) {
			response.setError(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}

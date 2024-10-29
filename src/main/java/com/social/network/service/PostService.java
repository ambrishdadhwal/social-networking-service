package com.social.network.service;

		import java.util.List;
		import java.util.Optional;
		import java.util.stream.Collectors;

		import org.springframework.stereotype.Service;

		import com.social.network.utils.UserPostMapper;
		import com.social.network.domain.UserPost;
		import com.social.network.entity.ProfileE;
		import com.social.network.entity.ProfileImageE;
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
	public Optional<UserPost> deleteUserPost(UserPost profilePost)
	{
		return Optional.empty();
	}

}

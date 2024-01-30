package com.expro.photogram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expro.photogram.domain.comment.Comment;
import com.expro.photogram.domain.comment.CommentRepository;
import com.expro.photogram.domain.image.Image;
import com.expro.photogram.domain.user.User;
import com.expro.photogram.domain.user.UserRepository;
import com.expro.photogram.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment commentWrite(String content, int imageId, int userId) {
		Image image = new Image();
		image.setId(imageId);
		
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void deleteComment(int id) {
		try {
			commentRepository.deleteById(id);
		}catch(Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
}

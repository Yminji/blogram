package com.expro.photogram.web.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.expro.photogram.cofig.auth.PrincipalDetails;
import com.expro.photogram.domain.comment.Comment;
import com.expro.photogram.service.CommentService;
import com.expro.photogram.web.dto.CMRespDto;
import com.expro.photogram.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		Comment comment = commentService.commentWrite(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기성공", comment), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.deleteComment(id);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "댓글삭제성공", null), HttpStatus.OK);
	}
	
}

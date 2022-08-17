package com.musiks.backend.comment;

import com.musiks.backend.auth.Auth;
import com.musiks.backend.music.MusicRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comments")
public class CommentController {
    Auth auth;
    MusicRepo musicRepo;
    CommentRepo commentRepo;

    @DeleteMapping("/{id}")
    void uncomment(HttpServletRequest req, @PathVariable long id) {
        var user = auth.getUser(req);
        var comment = commentRepo.findById(id);
        if (comment.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Comment not found"
        );
        if (comment.get().getOwner() != user) throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Unauthorized comment delete"
        );
        commentRepo.delete(comment.get());
    }
}

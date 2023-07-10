package com.sonhin.backend.comment;

import com.sonhin.backend.auth.Auth;
import com.sonhin.backend.music.MusicRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comments")
public class CommentController {
    Auth auth;
    MusicRepo musicRepo;
    CommentRepo commentRepo;

    @DeleteMapping("/{id}")
    void uncomment(HttpServletRequest req, @PathVariable Long id) {
        var user = auth.getUser(req);
        var comment = commentRepo.findById(id);
        if (comment.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Comment not found"
        );
        if (comment.get().owner != user) throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Unauthorized comment delete"
        );
        commentRepo.delete(comment.get());
    }

    @PatchMapping("/comments/{id}")
    Comment comment(@PathVariable Long id,
                    HttpServletRequest req,
                    @RequestBody String text) {
        var user = auth.getUser(req);
        var commentOptional = commentRepo.findById(id);
        if (commentOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Comment not found"
            );
        }
        var comment = commentOptional.get();
        if(!comment.owner.id.equals(user.id)){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized comment patch"
            );
        }
        comment.text = text;
        return commentRepo.save(comment);
    }

}

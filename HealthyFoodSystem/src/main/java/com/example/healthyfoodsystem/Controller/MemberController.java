package com.example.healthyfoodsystem.Controller;

import com.example.healthyfoodsystem.Api.ApiResponse;
import com.example.healthyfoodsystem.Model.Member;
import com.example.healthyfoodsystem.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/getMembers")
    public ResponseEntity getAllMembers(){
        return ResponseEntity.status(200).body(memberService.getAllMembers());
    }

    @PostMapping("/add")
    public ResponseEntity addMember(@Valid @RequestBody Member Member , Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        memberService.addMember(Member);
        return ResponseEntity.status(200).body(new ApiResponse("Added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMember(@PathVariable Integer id, @RequestBody @Valid Member Member, Errors errors)
    {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdated = memberService.update(id,Member);

        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("update failed"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMember(@PathVariable Integer id){
        Boolean isDelete= memberService.delete(id);
        if (isDelete){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("delete failed"));
    }


}

package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;
import co.muhu.eventManagement.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedBackController {
    public static final String FEEDBACK_PATH="/api/v1/feedback";
    public static final String FEEDBACK_PATH_ID="/api/v1/feedback/{feedBackId}";
    private final FeedbackService feedbackService;

    @GetMapping(value = FEEDBACK_PATH)
    public ResponseEntity<List<FeedBackDto>> getAllFeedBacks(){
        List<FeedBackDto> feedBackList = feedbackService.getAllFeedbacks();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH)
                .body(feedBackList);
    }

    @GetMapping(value = FEEDBACK_PATH_ID)
    public ResponseEntity<FeedBackDto> getFeedBackById(@PathVariable Long feedBackId){
        FeedBackDto foundedFeedBack = feedbackService.getFeedbackById(feedBackId)
                .orElseThrow(()-> new ResourceNotFoundException("There is no feedback with this id : "+feedBackId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH+"/"+feedBackId)
                .body(foundedFeedBack);
    }

    @PostMapping(value = FEEDBACK_PATH)
    public ResponseEntity<FeedBackDto> saveFeedBack(@Validated @RequestBody FeedBackRegistrationDto feedBackRegistrationDto){
        FeedBackDto savedFeedBack=feedbackService.createFeedback(feedBackRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH)
                .body(savedFeedBack);
    }

    @PutMapping(value = FEEDBACK_PATH_ID)
    public ResponseEntity<FeedBackDto> updateFeedBack(@Validated@RequestBody FeedBack feedBack,
                                                   @PathVariable Long feedBackId){
        FeedBackDto updatedFeedBack = feedbackService.updateFeedback(feedBackId, feedBack)
                .orElseThrow(()-> new ResourceNotFoundException("There is no feedback with this id : "+feedBackId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH+"/"+feedBackId)
                .body(updatedFeedBack);
    }

    @DeleteMapping(value = FEEDBACK_PATH_ID)
    public ResponseEntity<?> deleteFeedbackById(@PathVariable Long feedBackId){
        boolean isDeleted = feedbackService.deleteFeedbackById(feedBackId);
        if (!isDeleted){
            throw new ResourceNotFoundException("There is no feedback with this id : "+feedBackId);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH+"/"+feedBackId)
                .build();
    }

    @GetMapping(value = FEEDBACK_PATH+"/event/{eventId}")
    public ResponseEntity<List<FeedBackDto>> getAllFeedBacksByEventId(@PathVariable Long eventId){
        List<FeedBackDto> feedBackList = feedbackService.getAllFeedbacksByEventId(eventId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH)
                .body(feedBackList);
    }

    @GetMapping(value = FEEDBACK_PATH+"/participant/{participantId}")
    public ResponseEntity<List<FeedBackDto>> getAllFeedBacksByParticipantId(@PathVariable Long participantId){
        List<FeedBackDto> feedBackList = feedbackService.getAllFeedBacksByParticipantId(participantId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,FEEDBACK_PATH)
                .body(feedBackList);
    }
}

package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.ParticipantDto;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;
import co.muhu.eventManagement.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    public static final String PARTICIPANT_PATH="/api/v1/participant";
    public static final String PARTICIPANT_PATH_ID="/api/v1/participant/{participantId}";

    @GetMapping(value = PARTICIPANT_PATH)
    public ResponseEntity<List<ParticipantDto>> getAllParticipant(){
        List<ParticipantDto> participantList = participantService.getAllParticipants();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,PARTICIPANT_PATH)
                .body(participantList);
    }
    @GetMapping(value = PARTICIPANT_PATH_ID)
    public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable Long participantId){
        ParticipantDto foundedParticipant = participantService.getParticipantById(participantId)
                .orElseThrow(()-> new ResourceNotFoundException("There no participant with this id : "+participantId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,PARTICIPANT_PATH+"/"+participantId)
                .body(foundedParticipant);
    }

    @PostMapping(value = PARTICIPANT_PATH)
    public ResponseEntity<ParticipantDto> saveParticipant(@Validated @RequestBody ParticipantRegistrationDto participantRegistrationDto){
        ParticipantDto savedParticipant=participantService.createParticipant(participantRegistrationDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,PARTICIPANT_PATH)
                .body(savedParticipant);
    }

    @PutMapping(value = PARTICIPANT_PATH_ID)
    public ResponseEntity<ParticipantDto> updateParticipant(@Validated @RequestBody Participant participant,
                                                         @PathVariable Long participantId){
        ParticipantDto updatedParticipant= participantService.updateParticipant(participantId,participant)
                .orElseThrow(()-> new ResourceNotFoundException("There is no participant with this id : "+participantId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,PARTICIPANT_PATH+"/"+participantId)
                .body(updatedParticipant);
    }

    @DeleteMapping(value = PARTICIPANT_PATH_ID)
    public ResponseEntity<?> deleteParticipant(@PathVariable Long participantId){
        boolean isDeleted = participantService.deleteParticipantById(participantId);
        if (!isDeleted){
            throw new ResourceNotFoundException("There is no participant with this id : "+participantId);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,PARTICIPANT_PATH+"/"+participantId)
                .build();
    }

    @GetMapping(value = PARTICIPANT_PATH+"/event/{eventId}")
    public ResponseEntity<List<ParticipantDto>> getAllParticipantByEventId(@PathVariable Long eventId){
        List<ParticipantDto> participantList = participantService.getParticipantsByEventId(eventId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,PARTICIPANT_PATH)
                .body(participantList);
    };
}

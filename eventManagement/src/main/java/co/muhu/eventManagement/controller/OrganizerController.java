package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.OrganizerDto;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;
import co.muhu.eventManagement.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrganizerController {
    public static final String ORGANIZER_PATH = "/api/v1/organizer";
    public static final String ORGANIZER_PATH_ID = "/api/v1/organizer/{organizerId}";
    private final OrganizerService organizerService;

    @GetMapping(value = ORGANIZER_PATH)
    public ResponseEntity<List<OrganizerDto>> getAllOrganizers(){
        List<OrganizerDto> organizerList = organizerService.getAllOrganizers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,ORGANIZER_PATH)
                .body(organizerList);
    }

    @GetMapping(value = ORGANIZER_PATH_ID)
    public ResponseEntity<OrganizerDto> getOrganizerById(@PathVariable Long organizerId){
        OrganizerDto foundedOrganizer = organizerService.getOrganizerById(organizerId)
                .orElseThrow(()-> new ResourceNotFoundException("There is no organizer with this id : "+organizerId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,ORGANIZER_PATH+"/"+organizerId)
                .body(foundedOrganizer);
    }

    @PostMapping(value = ORGANIZER_PATH)
    public ResponseEntity<OrganizerDto> saveOrganizer(@Validated @RequestBody OrganizerRegistrationDto organizerRegistrationDto){
        OrganizerDto savedOrganizer = organizerService.createOrganizer(organizerRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,ORGANIZER_PATH)
                .body(savedOrganizer);
    }

    @PutMapping(value = ORGANIZER_PATH_ID)
    public ResponseEntity<OrganizerDto> updateOrganizer(@Validated @RequestBody Organizer organizer,
                                               @PathVariable Long organizerId){
        OrganizerDto updatedOrganizer = organizerService.updateOrganizer(organizerId, organizer)
                .orElseThrow(() -> new ResourceNotFoundException("There is no organizer with this id : " + organizerId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,ORGANIZER_PATH+"/"+organizerId)
                .body(updatedOrganizer);
    }

    @DeleteMapping(value = ORGANIZER_PATH_ID)
    public ResponseEntity<?> deletedOrganizer(@PathVariable Long organizerId){
        boolean isDeleted = organizerService.deleteOrganizerById(organizerId);
        if(!isDeleted){
            throw new ResourceNotFoundException("There is no organizer with this id : " + organizerId);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,ORGANIZER_PATH+"/"+organizerId)
                .build();
    }

    @GetMapping(value = ORGANIZER_PATH+"/event/{eventId}")
    public ResponseEntity<List<OrganizerDto>> getOrganizerByEventId(@PathVariable Long eventId){
        List<OrganizerDto> organizerList = organizerService.getOrganizerByEventId(eventId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,ORGANIZER_PATH+"/event/"+eventId)
                .body(organizerList);
    }



}

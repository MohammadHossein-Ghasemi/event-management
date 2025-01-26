package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.VenueDto;
import co.muhu.eventManagement.model.VenueRegistrationDto;
import co.muhu.eventManagement.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VenueController {
    private static final String VENUE_PATH = "/api/v1/venue";
    private static final String VENUE_PATH_ID = "/api/v1/venue/{venueId}";
    private final VenueService venueService;

    @GetMapping(value = VENUE_PATH)
    public ResponseEntity<List<VenueDto>> getAllVenues(){
        List<VenueDto> venueList = venueService.getAllVenues();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,VENUE_PATH)
                .body(venueList);
    }

    @GetMapping(value = VENUE_PATH_ID)
    public ResponseEntity<VenueDto> getVenueById(@PathVariable Long venueId){
        VenueDto foundedVenue = venueService.getVenueById(venueId)
                .orElseThrow(()->new ResourceNotFoundException("There is no venue by this id : "+venueId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,VENUE_PATH+"/"+venueId)
                .body(foundedVenue);

    }

    @PostMapping(value = VENUE_PATH)
    public ResponseEntity<VenueDto> saveVenue(@Validated @RequestBody VenueRegistrationDto venueRegistrationDto){
        VenueDto savedVenue = venueService.createVenue(venueRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,VENUE_PATH)
                .body(savedVenue);
    }

    @PutMapping(value = VENUE_PATH_ID)
    public ResponseEntity<VenueDto> updateVenue(@Validated @RequestBody Venue venue,
                                             @PathVariable Long venueId){
        VenueDto updatedVenue = venueService.updateVenue(venueId,venue)
                .orElseThrow(()->new ResourceNotFoundException("There is no venue with this id : "+venueId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,VENUE_PATH+"/"+venueId)
                .body(updatedVenue);
    }

    @DeleteMapping(value = VENUE_PATH_ID)
    public ResponseEntity<?> deleteVenue(@PathVariable Long venueId){
        boolean isDeleted = venueService.deleteVenueById(venueId);
        if(!isDeleted){
            throw new ResourceNotFoundException("There is no venue with this id : "+venueId);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,VENUE_PATH+"/"+venueId)
                .build();
    }
}

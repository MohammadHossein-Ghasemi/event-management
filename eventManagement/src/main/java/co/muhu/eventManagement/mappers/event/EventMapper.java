package co.muhu.eventManagement.mappers.event;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.mappers.feedback.FeedBackMapper;
import co.muhu.eventManagement.mappers.participant.ParticipantMapper;
import co.muhu.eventManagement.mappers.ticket.TicketMapper;
import co.muhu.eventManagement.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EventMapper{

    public static Event eventRegistrationDtoToEvent(EventRegistrationDto eventRegistrationDto) {
        if (eventRegistrationDto==null){
            return new Event();
        }
        return Event.builder()
                .name(eventRegistrationDto.getName())
                .description(eventRegistrationDto.getDescription())
                .location(eventRegistrationDto.getLocation())
                .startTime(eventRegistrationDto.getStartTime())
                .endTime(eventRegistrationDto.getEndTime())
                .member(eventRegistrationDto.getMember())
                .organizer(eventRegistrationDto.getOrganizer())
                .venue(eventRegistrationDto.getVenue())
                .participantSet(eventRegistrationDto.getParticipantSet())
                .build();
    }

    public static EventDto eventToEventDto(Event event) {
        if (event==null){
            return new EventDto();
        }
        List<ParticipantDto> participantDtoList = event.getParticipantSet()
                .stream()
                .map(ParticipantMapper::participantToParticipantDto)
                .toList();

        List<TicketDto> ticketDtoList = event.getTicketSet()
                .stream()
                .map(TicketMapper::ticketToTicketDto)
                .toList();

        List<FeedBackDto> feedBackDtoList = event.getFeedBackSet()
                .stream()
                .map(FeedBackMapper::feedBackToFeedBackDto)
                .toList();

        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getName())
                .location(event.getLocation())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .createdTime(event.getCreatedTime())
                .updatedTime(event.getUpdatedTime())
                .memberId(event.getMember().getId())
                .organizerId(event.getOrganizer().getId())
                .venueId(event.getVenue().getId())
                .participantIds(participantDtoList)
                .ticketIds(ticketDtoList)
                .feedbackIds(feedBackDtoList)
                .build();
    }
}

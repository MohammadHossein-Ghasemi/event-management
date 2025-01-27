package co.muhu.eventManagement.mappers.participant;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.mappers.feedback.FeedBackMapper;
import co.muhu.eventManagement.mappers.ticket.TicketMapper;
import co.muhu.eventManagement.model.*;

import java.util.List;


public class ParticipantMapper {
    public static Participant participantRegistrationDtoToParticipant(ParticipantRegistrationDto participantRegistrationDto) {
        if (participantRegistrationDto==null){
            return new Participant();
        }
        return Participant.builder()
                .name(participantRegistrationDto.getName())
                .email(participantRegistrationDto.getEmail())
                .phone(participantRegistrationDto.getPhone())
                .build();
    }

    public static ParticipantDto participantToParticipantDto(Participant participant){
        if (participant==null){
            return new ParticipantDto();
        }
        List<EventDto> eventDtoList = participant.getEventSet()
                .stream()
                .map(EventMapper::eventToEventDto)
                .toList();
        List<TicketDto> ticketDtoList= participant.getTicketSet()
                .stream()
                .map(TicketMapper::ticketToTicketDto)
                .toList();
        List<FeedBackDto> feedBackDtoList = participant.getFeedBackSet()
                .stream()
                .map(FeedBackMapper::feedBackToFeedBackDto)
                .toList();
        return ParticipantDto.builder()
                .id(participant.getId())
                .name(participant.getName())
                .email(participant.getEmail())
                .phone(participant.getPhone())
                .registrationDate(participant.getRegistrationDate())
                .status(participant.getStatus())
                .eventSet(eventDtoList)
                .ticketSet(ticketDtoList)
                .feedBackSet(feedBackDtoList)
                .build();
    }
}

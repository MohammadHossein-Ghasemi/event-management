package co.muhu.eventManagement.mappers.participant;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.mappers.feedback.FeedBackMapper;
import co.muhu.eventManagement.mappers.ticket.TicketMapper;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.ParticipantDto;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;
import co.muhu.eventManagement.model.TicketDto;

import java.util.List;


public class ParticipantMapper {
    public static Participant participantRegistrationDtoToParticipant(ParticipantRegistrationDto participantRegistrationDto) {
        return Participant.builder()
                .name(participantRegistrationDto.getName())
                .email(participantRegistrationDto.getEmail())
                .phone(participantRegistrationDto.getPhone())
                .build();
    }

    public static ParticipantDto participantToParticipantDto(Participant participant){
        List<Long> eventId = participant.getEventSet()
                .stream()
                .map(Event::getId)
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
                .eventSet(eventId)
                .ticketSet(ticketDtoList)
                .feedBackSet(feedBackDtoList)
                .build();
    }
}

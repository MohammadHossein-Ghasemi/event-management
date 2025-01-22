package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.repository.ParticipantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ParticipantServiceImplTest {

    private ParticipantServiceImpl participantServiceTest;

    @Mock
    private ParticipantRepository participantRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        participantServiceTest=new ParticipantServiceImpl(participantRepositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();;
    }

    @Test
    void getAllParticipants() {
        participantServiceTest.getAllParticipants();
        verify(participantRepositoryMock).findAll();
    }

    @Test
    void getParticipantById() {
        long id =1;

        participantServiceTest.getParticipantById(id);

        verify(participantRepositoryMock).findById(id);
    }

    @Test
    void createParticipant() {
        long id =1;
        Participant newParticipant = Participant.builder().id(id).build();

        participantServiceTest.createParticipant(newParticipant);

        verify(participantRepositoryMock).save(any(Participant.class));
    }

    @Test
    void updateParticipant() {
        long id =1;
        Participant exitingParticipant = Participant.builder().id(id).build();
        Participant updateParticipant = Participant.builder().id(id).name("Update Participant").build();

        when(participantRepositoryMock.findById(exitingParticipant.getId())).thenReturn(Optional.of(exitingParticipant));
        when(participantRepositoryMock.save(any(Participant.class))).thenReturn(updateParticipant);

        Optional<Participant> result = participantServiceTest.updateParticipant(exitingParticipant.getId(), updateParticipant);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(
                updatedParticipant->{
                    assertThat(updatedParticipant.getId()).isEqualTo(exitingParticipant.getId());
                    assertThat(updatedParticipant.getName()).isEqualTo(updatedParticipant.getName());
                }
        );

        verify(participantRepositoryMock).findById(exitingParticipant.getId());
        verify(participantRepositoryMock).save(any(Participant.class));
    }
    @Test
    void updateParticipantWhenParticipantNotPresent() {
        long id =1;
        Participant exitingParticipant = Participant.builder().id(id).build();
        Participant updateParticipant = Participant.builder().id(id).name("Update Participant").build();

        when(participantRepositoryMock.findById(exitingParticipant.getId())).thenReturn(Optional.empty());

        Optional<Participant> result = participantServiceTest.updateParticipant(exitingParticipant.getId(), updateParticipant);

        assertThat(result).isNotPresent();

        verify(participantRepositoryMock).findById(exitingParticipant.getId());
    }
    @Test
    void deleteParticipantById() {
        long id =1;

        when(participantRepositoryMock.existsById(id)).thenReturn(true);

        boolean result = participantServiceTest.deleteParticipantById(id);

        assertThat(result).isTrue();

        verify(participantRepositoryMock).deleteById(id);
    }
    @Test
    void deleteParticipantByIdWhenParticipantNotPresent() {
        long id =1;

        when(participantRepositoryMock.existsById(id)).thenReturn(false);

        boolean result = participantServiceTest.deleteParticipantById(id);

        assertThat(result).isFalse();
    }

    @Test
    void getParticipantsByEventId() {
        long eventId=1;

        participantServiceTest.getParticipantsByEventId(eventId);

        verify(participantRepositoryMock).findAllByEventSetId(eventId);
    }
}
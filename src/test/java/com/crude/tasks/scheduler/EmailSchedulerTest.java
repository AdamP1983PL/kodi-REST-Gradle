package com.crude.tasks.scheduler;

import com.crude.tasks.config.AdminConfig;
import com.crude.tasks.domain.Mail;
import com.crude.tasks.repository.TaskRepository;
import com.crude.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Test
    void shouldSendEMailInformation() {
        // given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("podlaski83@gmail.com");

        // when
        emailScheduler.sendInformationEmail();

        // then
        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }

}

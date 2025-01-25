package com.example.borrowing.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.borrowing.Model.BorrowingEntity;
import com.example.borrowing.Model.BorrowingEntity.Status;
import com.example.borrowing.Repository.BorrowingRepository;
import com.example.borrowing.Service.BorrowingService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    @Test
    void testGetByEmplId() {
        String id = "2";

        // BorrowingEntity borrowingEntity = new BorrowingEntity("2", );
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        BorrowingEntity borrowingEntity = new BorrowingEntity(
            instant,
            Status.BORROWED,
            "2",
            "2"
        );
        BorrowingEntity borrowingEntity2 = new BorrowingEntity(
            instant,
            Status.CANCELED,
            "3",
            "2"
        );
        BorrowingEntity borrowingEntity3 = new BorrowingEntity(
            instant,
            Status.RETURNED,
            "4",
            "2"
        );

        List<BorrowingEntity> borrowingEntities = Arrays.asList(
            borrowingEntity,
            borrowingEntity2,
            borrowingEntity3
        );

        when(borrowingRepository.findByEmployeeId(id)).thenReturn(
            borrowingEntities
        );

        List<BorrowingEntity> result = borrowingService.getByEmplId(id);
        System.out.println("sdasdfasdfadf" + result);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("2", result.get(0).getBookId());
        assertEquals("3", result.get(1).getBookId());
        assertEquals("4", result.get(2).getBookId());
    }
}

package com.example.book.constant;
 
import org.springframework.beans.factory.annotation.Value;

public class KafkaConstants {

    public static final String BORROWING_GROUP = "borrowing-group";

    public static final String BOOK_GROUP = "book-group";

    public static final String EMPL_GROUP = "empl-group";

    public static final String BOOK_TOPIC = "borrowing-book-topic";
    public static final String EMP_TOPIC = "borrowing-empl-topic";

    public static final String BORROWING_REQUEST = "borrowing-request-topic";

    public static final String BORROWING_APPROVAL = "borrowing-approval-topic";
    public static final String BOOK_VALIDATION_TOPIC = "book-validation-topic";
    public static final String EMPL_VALIDATION_TOPIC = "emp-validation-topic";

    public static final String BORROWING_VALIDATION_TOPIC =
        "borrowing-validation-topic";

    public static final String BORROWING_UPDATE = "borrowing-updated-topic";

    public static final String BORROWED_BOOK = "borrowed-book-topic";
}

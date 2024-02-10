package com.openclassrooms.tourguide;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TourguideApplicationTests {

	@Test
	void contextLoads() {
		   TourguideApplication application = new TourguideApplication();
        assertNotNull(application);
	}
}
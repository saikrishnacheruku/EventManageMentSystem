package com.example.eventmanagementsystem.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.EventRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventJpaService implements EventRepository {

    @Autowired
    private EventJpaRepository eventJpaRepository;
    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public List<Event> getEvents() {
        return eventJpaRepository.findAll();
    }

    @Override
    public Event getEventById(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();

            return event;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event addEvent(Event event) {
        List<Integer> sponsorIds = new ArrayList<>();
        for (Sponsor sponsor : event.getSponsors()) {
            sponsorIds.add(sponsor.getSponsorId());
        }
        List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
        event.setSponsors(sponsors);
        for (Sponsor sponsor : sponsors) {
            sponsor.getEvents().add(event);
        }
        Event savedEvent = eventJpaRepository.save(event);
        sponsorJpaRepository.saveAll(sponsors);
        return savedEvent;
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        try {
            Event newEvent = eventJpaRepository.findById(eventId).get();

            if (event.getEventName() != null) {
                newEvent.setEventName(event.getEventName());
            }
            if (event.getDate() != null) {
                newEvent.setDate(event.getDate());
            }
            if (event.getSponsors() != null) {
                List<Sponsor> sponsors = newEvent.getSponsors();
                for (Sponsor sponsor : sponsors) {
                    sponsor.getEvents().remove(newEvent);
                }
                sponsorJpaRepository.saveAll(sponsors);
                List<Integer> newSponsorIds = new ArrayList<>();
                for (Sponsor sponsor : event.getSponsors()) {
                    newSponsorIds.add(sponsor.getSponsorId());

                }
                List<Sponsor> newSponsors = sponsorJpaRepository.findAllById(newSponsorIds);
                for (Sponsor sponsor : newSponsors) {
                    sponsor.getEvents().add(newEvent);
                }
                sponsorJpaRepository.saveAll(newSponsors);
                newEvent.setSponsors(newSponsors);
            }
            return eventJpaRepository.save(newEvent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

  @Override
    public void deleteEvent(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();


            List<Sponsor> sponsors = event.getSponsors();
            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().remove(event);


            }
            sponsorJpaRepository.saveAll(sponsors);
            eventJpaRepository.deleteById(eventId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); //updated (NO_CONTENT to NOT_FOUND)
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);//updated (NOT_FOUND to NO_CONTENT 
    }

    @Override
    public List<Sponsor> getEventSponsors(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            return event.getSponsors();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
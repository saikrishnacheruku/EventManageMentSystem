package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.Event;
import com.example.eventmanagementsystem.model.Sponsor;
import com.example.eventmanagementsystem.repository.EventJpaRepository;
import com.example.eventmanagementsystem.repository.SponsorJpaRepository;
import com.example.eventmanagementsystem.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

@Service

public class SponsorJpaService implements SponsorRepository {

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public List<Sponsor> getSponsors() {
        return sponsorJpaRepository.findAll();
    }

    @Override
    public Sponsor getSponsorById(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            return sponsor;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        List<Integer> eventIds = new ArrayList<>();
        for (Event event : sponsor.getEvents()) {
            eventIds.add(event.getEventId());
        }
        List<Event> events = eventJpaRepository.findAllById(eventIds);

        if (events.size() != eventIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
        sponsor.setEvents(events);
        return sponsorJpaRepository.save(sponsor);
    }

    @Override
    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        try {
            Sponsor sponsor1 = sponsorJpaRepository.findById(sponsorId).get();

            if (sponsor.getSponsorName() != null) {
                sponsor1.setSponsorName(sponsor.getSponsorName());

            }
            if (sponsor.getIndustry() != null) {
                sponsor1.setIndustry(sponsor.getIndustry());

            }
            if (sponsor.getEvents() != null) {
                List<Integer> eventIds = new ArrayList<>();

                for (Event event : sponsor.getEvents()) {
                    eventIds.add(event.getEventId());

                }
                List<Event> events = eventJpaRepository.findAllById(eventIds);
                if (events.size() != eventIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                sponsor1.setEvents(events);
            }
            return sponsorJpaRepository.save(sponsor1);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        try {
            sponsorJpaRepository.deleteById(sponsorId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public List<Event> getSponsorEvents(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            return sponsor.getEvents();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;
import static java.time.LocalDateTime.now;
@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Semen", "Java junior developer", now(), 0));
        save(new Candidate(0, "Petr", "Java junior developer", now(), 0));
        save(new Candidate(0, "Vladimir", "Java middle developer", now(), 0));
        save(new Candidate(0, "Timur", "Java senior developer", now(), 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(
                candidate.getId(), (id, oldCandidate) -> new Candidate(
                        id, candidate.getName(), candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}

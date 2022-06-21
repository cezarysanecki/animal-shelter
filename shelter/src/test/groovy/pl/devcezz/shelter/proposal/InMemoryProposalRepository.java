package pl.devcezz.shelter.proposal;

import pl.devcezz.shelter.InMemoryRepository;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryProposalRepository extends InMemoryRepository<Proposal, Long> implements ProposalRepository {

    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public <S extends Proposal> S save(S entity) {
        if (entity.getId() == null) {
            setId(entity);
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Proposal> findProposalBySubjectId(SubjectId subjectId) {
        return entities.values()
                .stream()
                .filter(proposal -> proposal.getSubjectId().equals(subjectId))
                .findFirst();
    }

    private <S extends Proposal> void setId(S entity) {
        try {
            Class<? extends Proposal> clazz = entity.getClass();
            Field id = clazz.getDeclaredField("id");
            id.setAccessible(true);
            id.set(entity, idSequence.incrementAndGet());
        } catch (Exception e) {
            throw new IllegalStateException("cannot set id", e);
        }
    }

}

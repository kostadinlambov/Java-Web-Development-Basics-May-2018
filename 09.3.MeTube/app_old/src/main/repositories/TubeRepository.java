package repositories;

import entities.Tube;

import java.util.ArrayList;
import java.util.List;

public class TubeRepository extends BaseRepository {
    public void createTube(Tube tube) {
        this.execute(actionResult -> {
            this.entityManager.persist(tube);
        });
    }

    public List<Tube> findAllTubes() {
        List<Tube> result = new ArrayList<>();

        this.execute(actionResult -> {
            result.addAll(this.entityManager.createNativeQuery(
                    "SELECT * FROM tubes AS t ", Tube.class
            ).getResultList());
        });

        return result;
    }

    public List<Tube> findUserUploadedTubes(String userId) {
        List<Tube> result = new ArrayList<>();

        this.execute(actionResult -> {
            result.addAll(this.entityManager.createNativeQuery(
                    "SELECT * FROM tubes AS t " +
                            "WHERE t.uploader_id = \'" + userId + "\' ", Tube.class
            ).getResultList());
        });

        return result;
    }

    public Tube findById(String tubeId) {
        Tube tube = (Tube) this.execute(actionResult -> {
            Object queryResult =
                    this.entityManager.createNativeQuery("SELECT * FROM tubes AS t WHERE t.id = \'" + tubeId + "\'", Tube.class)
                            .getResultList()
                            .stream()
                            .findFirst()
                            .orElse(null);

            actionResult.setResult(queryResult);
        }).getResult();

        return tube;
    }

    public void incrementViews(String tubeId) {
        this.execute(actionResult -> {
            this.entityManager.createNativeQuery("UPDATE tubes " +
                    "SET views = views + 1 " +
                    "WHERE id = \'" + tubeId + "\'")
                    .executeUpdate();
        });
    }
}

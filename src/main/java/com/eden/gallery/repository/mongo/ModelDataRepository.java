package com.eden.gallery.repository.mongo;

import com.eden.gallery.model.ModelData;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for crawled model data.
 */
@Repository
public interface ModelDataRepository extends MongoRepository<ModelData, ObjectId> {

    /**
     * Find a list of model to move by [skip, numberOfAlbum, moved].
     *
     * @param numberOfAlbum threshold for number of album
     * @param pageable      paging data.
     * @return list of model data
     */
    Page<ModelData> findModelDataBySkipIsFalseAndMovedIsFalseAndNumberOfAlbumGreaterThan(
            int numberOfAlbum,
            Pageable pageable
    );

    /**
     * Find a list of model data by average rating threshold.
     *
     * @param avg      average point larger than
     * @param pageable paging data
     * @return list of model data
     */
    Page<ModelData> findModelDataByAvgGreaterThan(float avg, Pageable pageable);
}

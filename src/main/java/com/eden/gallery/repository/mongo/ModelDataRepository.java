package com.eden.gallery.repository.mongo;

import com.eden.gallery.model.ModelData;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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

    /**
     * Find model by name in model name and tags.
     *
     * @param name          name to find
     * @param numberOfAlbum minimum number of album exclusive
     * @param pageable      page information
     * @return a page of model data
     */
    @Query("{ $or: ["
            + "{'name': { $regex: ?0, $options: 'i' }, 'skip': false, 'moved': false, 'numberOfAlbum': {$gt: ?1}}, "
            + "{$and: ["
            + "{'skip': false, 'moved': false, 'numberOfAlbum': {$gt: ?1}}, "
            + "{'rel': {$elemMatch: {'name': {$regex: ?0, $options: 'i'}, 'isPublisher': false, 'isCategory': false }}}"
            + "]}"
            + "]}")
    Page<ModelData> findModelByName(String name, int numberOfAlbum, Pageable pageable);
}

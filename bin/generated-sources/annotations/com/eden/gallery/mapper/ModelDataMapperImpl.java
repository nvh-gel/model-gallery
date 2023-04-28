package com.eden.gallery.mapper;

import com.eden.gallery.model.ModelData;
import com.eden.gallery.viewmodel.ModelDataVM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-28T10:11:55+0700",
    comments = "version: 1.5.4.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230413-0857, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class ModelDataMapperImpl implements ModelDataMapper {

    @Override
    public void mapUpdate(ModelData arg0, ModelData arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getCreatedAt() != null ) {
            arg0.setCreatedAt( arg1.getCreatedAt() );
        }
        arg0.setDeleted( arg1.isDeleted() );
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getUpdatedAt() != null ) {
            arg0.setUpdatedAt( arg1.getUpdatedAt() );
        }
        if ( arg1.getUuid() != null ) {
            arg0.setUuid( arg1.getUuid() );
        }
        if ( arg1.getAvg() != null ) {
            arg0.setAvg( arg1.getAvg() );
        }
        if ( arg1.getBb() != null ) {
            arg0.setBb( arg1.getBb() );
        }
        if ( arg1.getBd() != null ) {
            arg0.setBd( arg1.getBd() );
        }
        if ( arg1.getCt() != null ) {
            arg0.setCt( arg1.getCt() );
        }
        if ( arg1.getFc() != null ) {
            arg0.setFc( arg1.getFc() );
        }
        if ( arg1.getHi() != null ) {
            arg0.setHi( arg1.getHi() );
        }
        if ( arg0.getImages() != null ) {
            List<String> list = arg1.getImages();
            if ( list != null ) {
                arg0.getImages().clear();
                arg0.getImages().addAll( list );
            }
        }
        else {
            List<String> list = arg1.getImages();
            if ( list != null ) {
                arg0.setImages( new ArrayList<String>( list ) );
            }
        }
        if ( arg1.getMoved() != null ) {
            arg0.setMoved( arg1.getMoved() );
        }
        if ( arg1.getName() != null ) {
            arg0.setName( arg1.getName() );
        }
        if ( arg1.getNeedCrawl() != null ) {
            arg0.setNeedCrawl( arg1.getNeedCrawl() );
        }
        if ( arg1.getNumberOfAlbum() != null ) {
            arg0.setNumberOfAlbum( arg1.getNumberOfAlbum() );
        }
        if ( arg0.getRel() != null ) {
            Set<String> set = arg1.getRel();
            if ( set != null ) {
                arg0.getRel().clear();
                arg0.getRel().addAll( set );
            }
        }
        else {
            Set<String> set = arg1.getRel();
            if ( set != null ) {
                arg0.setRel( new LinkedHashSet<String>( set ) );
            }
        }
        if ( arg1.getSkip() != null ) {
            arg0.setSkip( arg1.getSkip() );
        }
        if ( arg1.getSx() != null ) {
            arg0.setSx( arg1.getSx() );
        }
        if ( arg1.getUrl() != null ) {
            arg0.setUrl( arg1.getUrl() );
        }
        if ( arg1.getWa() != null ) {
            arg0.setWa( arg1.getWa() );
        }
    }

    @Override
    public Collection<ModelData> toDocument(Collection<ModelDataVM> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Collection<ModelData> collection = new ArrayList<ModelData>( arg0.size() );
        for ( ModelDataVM modelDataVM : arg0 ) {
            collection.add( toDocument( modelDataVM ) );
        }

        return collection;
    }

    @Override
    public ModelDataVM toViewModel(ModelData arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ModelDataVM modelDataVM = new ModelDataVM();

        modelDataVM.setObjectId( mapObjectIdToString( arg0.getId() ) );
        modelDataVM.setCreatedAt( arg0.getCreatedAt() );
        modelDataVM.setUpdatedAt( arg0.getUpdatedAt() );
        modelDataVM.setUuid( arg0.getUuid() );
        modelDataVM.setAvg( arg0.getAvg() );
        modelDataVM.setBb( arg0.getBb() );
        modelDataVM.setBd( arg0.getBd() );
        modelDataVM.setCt( arg0.getCt() );
        modelDataVM.setFc( arg0.getFc() );
        modelDataVM.setHi( arg0.getHi() );
        List<String> list = arg0.getImages();
        if ( list != null ) {
            modelDataVM.setImages( new ArrayList<String>( list ) );
        }
        modelDataVM.setMoved( arg0.getMoved() );
        modelDataVM.setName( arg0.getName() );
        modelDataVM.setNumberOfAlbum( arg0.getNumberOfAlbum() );
        Set<String> set = arg0.getRel();
        if ( set != null ) {
            modelDataVM.setRel( new LinkedHashSet<String>( set ) );
        }
        modelDataVM.setSx( arg0.getSx() );
        modelDataVM.setUrl( arg0.getUrl() );
        modelDataVM.setWa( arg0.getWa() );

        return modelDataVM;
    }

    @Override
    public Collection<ModelDataVM> toViewModel(Collection<ModelData> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Collection<ModelDataVM> collection = new ArrayList<ModelDataVM>( arg0.size() );
        for ( ModelData modelData : arg0 ) {
            collection.add( toViewModel( modelData ) );
        }

        return collection;
    }

    @Override
    public ModelData toDocument(ModelDataVM modelDataVM) {
        if ( modelDataVM == null ) {
            return null;
        }

        ModelData modelData = new ModelData();

        modelData.setId( mapStringToObjectId( modelDataVM.getObjectId() ) );
        modelData.setCreatedAt( modelDataVM.getCreatedAt() );
        modelData.setUpdatedAt( modelDataVM.getUpdatedAt() );
        modelData.setUuid( modelDataVM.getUuid() );
        modelData.setAvg( modelDataVM.getAvg() );
        modelData.setBb( modelDataVM.getBb() );
        modelData.setBd( modelDataVM.getBd() );
        modelData.setCt( modelDataVM.getCt() );
        modelData.setFc( modelDataVM.getFc() );
        modelData.setHi( modelDataVM.getHi() );
        List<String> list = modelDataVM.getImages();
        if ( list != null ) {
            modelData.setImages( new ArrayList<String>( list ) );
        }
        modelData.setMoved( modelDataVM.getMoved() );
        modelData.setName( modelDataVM.getName() );
        modelData.setNumberOfAlbum( modelDataVM.getNumberOfAlbum() );
        Set<String> set = modelDataVM.getRel();
        if ( set != null ) {
            modelData.setRel( new LinkedHashSet<String>( set ) );
        }
        modelData.setSx( modelDataVM.getSx() );
        modelData.setUrl( modelDataVM.getUrl() );
        modelData.setWa( modelDataVM.getWa() );

        return modelData;
    }
}

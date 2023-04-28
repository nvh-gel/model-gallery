package com.eden.gallery.mapper;

import com.eden.gallery.model.Model;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.gallery.viewmodel.NicknameVM;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-28T10:11:55+0700",
    comments = "version: 1.5.4.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230413-0857, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class ModelMapperImpl implements ModelMapper {

    @Override
    public void mapUpdate(Model arg0, Model arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getCreatedAt() != null ) {
            arg0.setCreatedAt( arg1.getCreatedAt() );
        }
        arg0.setDeleted( arg1.isDeleted() );
        arg0.setId( arg1.getId() );
        if ( arg1.getUpdatedAt() != null ) {
            arg0.setUpdatedAt( arg1.getUpdatedAt() );
        }
        if ( arg1.getUuid() != null ) {
            arg0.setUuid( arg1.getUuid() );
        }
        if ( arg1.getDateOfBirth() != null ) {
            arg0.setDateOfBirth( arg1.getDateOfBirth() );
        }
        if ( arg1.getLocalName() != null ) {
            arg0.setLocalName( arg1.getLocalName() );
        }
        if ( arg1.getName() != null ) {
            arg0.setName( arg1.getName() );
        }
        if ( arg0.getNicknames() != null ) {
            List<Nickname> list = arg1.getNicknames();
            if ( list != null ) {
                arg0.getNicknames().clear();
                arg0.getNicknames().addAll( list );
            }
        }
        else {
            List<Nickname> list = arg1.getNicknames();
            if ( list != null ) {
                arg0.setNicknames( new ArrayList<Nickname>( list ) );
            }
        }
        if ( arg1.getThumbnail() != null ) {
            arg0.setThumbnail( arg1.getThumbnail() );
        }
        if ( arg1.getUrl() != null ) {
            arg0.setUrl( arg1.getUrl() );
        }
        if ( arg1.getYearOfBirth() != null ) {
            arg0.setYearOfBirth( arg1.getYearOfBirth() );
        }
    }

    @Override
    public List<Model> toModel(List<ModelVM> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Model> list = new ArrayList<Model>( arg0.size() );
        for ( ModelVM modelVM : arg0 ) {
            list.add( toModel( modelVM ) );
        }

        return list;
    }

    @Override
    public ModelVM toViewModel(Model arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ModelVM modelVM = new ModelVM();

        modelVM.setCreatedAt( arg0.getCreatedAt() );
        modelVM.setId( arg0.getId() );
        modelVM.setUpdatedAt( arg0.getUpdatedAt() );
        modelVM.setUuid( arg0.getUuid() );
        modelVM.setDateOfBirth( arg0.getDateOfBirth() );
        modelVM.setLocalName( arg0.getLocalName() );
        modelVM.setName( arg0.getName() );
        modelVM.setNicknames( nicknameListToNicknameVMList( arg0.getNicknames() ) );
        modelVM.setThumbnail( arg0.getThumbnail() );
        modelVM.setUrl( arg0.getUrl() );
        modelVM.setYearOfBirth( arg0.getYearOfBirth() );

        return modelVM;
    }

    @Override
    public List<ModelVM> toViewModel(List<Model> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ModelVM> list = new ArrayList<ModelVM>( arg0.size() );
        for ( Model model : arg0 ) {
            list.add( toViewModel( model ) );
        }

        return list;
    }

    @Override
    public Model toModel(ModelVM modelVM) {
        if ( modelVM == null ) {
            return null;
        }

        Model model = new Model();

        model.setCreatedAt( modelVM.getCreatedAt() );
        if ( modelVM.getId() != null ) {
            model.setId( modelVM.getId() );
        }
        model.setUpdatedAt( modelVM.getUpdatedAt() );
        model.setUuid( modelVM.getUuid() );
        model.setDateOfBirth( modelVM.getDateOfBirth() );
        model.setLocalName( modelVM.getLocalName() );
        model.setName( modelVM.getName() );
        model.setNicknames( nicknameVMListToNicknameList( modelVM.getNicknames() ) );
        model.setThumbnail( modelVM.getThumbnail() );
        model.setUrl( modelVM.getUrl() );
        model.setYearOfBirth( modelVM.getYearOfBirth() );

        return model;
    }

    @Override
    public NicknameVM toViewModel(Nickname nickname) {
        if ( nickname == null ) {
            return null;
        }

        NicknameVM nicknameVM = new NicknameVM();

        nicknameVM.setModelId( modelToModelId( nickname.getModel() ) );
        nicknameVM.setCreatedAt( nickname.getCreatedAt() );
        nicknameVM.setId( nickname.getId() );
        nicknameVM.setUpdatedAt( nickname.getUpdatedAt() );
        nicknameVM.setUuid( nickname.getUuid() );
        nicknameVM.setNick( nickname.getNick() );

        return nicknameVM;
    }

    @Override
    public Nickname toModel(NicknameVM nicknameVM) {
        if ( nicknameVM == null ) {
            return null;
        }

        Nickname nickname = new Nickname();

        nickname.setCreatedAt( nicknameVM.getCreatedAt() );
        if ( nicknameVM.getId() != null ) {
            nickname.setId( nicknameVM.getId() );
        }
        nickname.setUpdatedAt( nicknameVM.getUpdatedAt() );
        nickname.setUuid( nicknameVM.getUuid() );
        nickname.setNick( nicknameVM.getNick() );

        return nickname;
    }

    protected List<NicknameVM> nicknameListToNicknameVMList(List<Nickname> list) {
        if ( list == null ) {
            return null;
        }

        List<NicknameVM> list1 = new ArrayList<NicknameVM>( list.size() );
        for ( Nickname nickname : list ) {
            list1.add( toViewModel( nickname ) );
        }

        return list1;
    }

    protected List<Nickname> nicknameVMListToNicknameList(List<NicknameVM> list) {
        if ( list == null ) {
            return null;
        }

        List<Nickname> list1 = new ArrayList<Nickname>( list.size() );
        for ( NicknameVM nicknameVM : list ) {
            list1.add( toModel( nicknameVM ) );
        }

        return list1;
    }
}

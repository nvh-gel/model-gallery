package com.eden.gallery.mapper;

import com.eden.gallery.model.Nickname;
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
public class NicknameMapperImpl implements NicknameMapper {

    @Override
    public void mapUpdate(Nickname arg0, Nickname arg1) {
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
        if ( arg1.getModel() != null ) {
            arg0.setModel( arg1.getModel() );
        }
        if ( arg1.getNick() != null ) {
            arg0.setNick( arg1.getNick() );
        }
    }

    @Override
    public List<Nickname> toModel(List<NicknameVM> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Nickname> list = new ArrayList<Nickname>( arg0.size() );
        for ( NicknameVM nicknameVM : arg0 ) {
            list.add( toModel( nicknameVM ) );
        }

        return list;
    }

    @Override
    public List<NicknameVM> toViewModel(List<Nickname> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<NicknameVM> list = new ArrayList<NicknameVM>( arg0.size() );
        for ( Nickname nickname : arg0 ) {
            list.add( toViewModel( nickname ) );
        }

        return list;
    }

    @Override
    public Nickname toModel(NicknameVM nicknameVM) {
        if ( nicknameVM == null ) {
            return null;
        }

        Nickname nickname = new Nickname();

        nickname.setModel( modelIdToModel( nicknameVM.getModelId() ) );
        nickname.setCreatedAt( nicknameVM.getCreatedAt() );
        if ( nicknameVM.getId() != null ) {
            nickname.setId( nicknameVM.getId() );
        }
        nickname.setUpdatedAt( nicknameVM.getUpdatedAt() );
        nickname.setUuid( nicknameVM.getUuid() );
        nickname.setNick( nicknameVM.getNick() );

        return nickname;
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
}

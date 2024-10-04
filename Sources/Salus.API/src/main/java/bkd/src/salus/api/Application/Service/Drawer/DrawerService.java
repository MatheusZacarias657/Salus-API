package bkd.src.salus.api.Application.Service.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.DetailingDrawerDTO;
import bkd.src.salus.api.Domain.DTO.Drawer.RegisterDrawerDTO;
import bkd.src.salus.api.Domain.Entity.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.Drawer.DrawerGroup;
import bkd.src.salus.api.Domain.Entity.User.UserAccount;
import bkd.src.salus.api.Domain.Interface.Application.Drawer.IDrawerService;
import bkd.src.salus.api.Repository.Drawer.IDrawerGroupRepositoryJPA;
import bkd.src.salus.api.Repository.Drawer.IDrawerRepositoryJPA;
import bkd.src.salus.api.Repository.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrawerService implements IDrawerService {

    private final IDrawerRepositoryJPA drawerRepository;
    private final IDrawerGroupRepositoryJPA drawerGroupRepository;
    private final IUserRepositoryJPA userRepository;

    @Autowired
    public DrawerService(IDrawerRepositoryJPA drawerRepository, IDrawerGroupRepositoryJPA drawerGroupRepository, IUserRepositoryJPA userRepository) {
        this.drawerRepository = drawerRepository;
        this.drawerGroupRepository = drawerGroupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DetailingDrawerDTO Register(RegisterDrawerDTO register, int userId){
        Drawer drawerEntity = new Drawer(register);
        drawerRepository.save(drawerEntity);

        UserAccount user = userRepository.getReferenceById(userId);
        DrawerGroup drawerGroup = new DrawerGroup(drawerEntity, user);
        drawerGroup.AddUser(user);
        drawerGroupRepository.save(drawerGroup);

        return  new DetailingDrawerDTO(drawerEntity, user);
    }
}

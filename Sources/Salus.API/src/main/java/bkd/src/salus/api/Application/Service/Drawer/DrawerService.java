package bkd.src.salus.api.Application.Service.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.DetailingDrawerDTO;
import bkd.src.salus.api.Domain.DTO.Drawer.RegisterDrawerDTO;
import bkd.src.salus.api.Domain.Entity.NoSQL.Topic.MqttTopic;
import bkd.src.salus.api.Domain.Entity.SQL.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.SQL.Drawer.DrawerGroup;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.Drawer.IDrawerService;
import bkd.src.salus.api.Repository.NoSQL.Topic.ITopicRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Drawer.IDrawerGroupRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Drawer.IDrawerRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DrawerService implements IDrawerService {

    private final IDrawerRepositoryJPA drawerRepository;
    private final IDrawerGroupRepositoryJPA drawerGroupRepository;
    private final IUserRepositoryJPA userRepository;
    private final ITopicRepositoryMR topicRepository;

    @Autowired
    public DrawerService(IDrawerRepositoryJPA drawerRepository, IDrawerGroupRepositoryJPA drawerGroupRepository, IUserRepositoryJPA userRepository, ITopicRepositoryMR topicRepository) {
        this.drawerRepository = drawerRepository;
        this.drawerGroupRepository = drawerGroupRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public DetailingDrawerDTO Register(RegisterDrawerDTO register, int userId){
        if (drawerRepository.findByEspId(register.getEspId()) != null){
            throw new ValidationException( "This drawer already exists");
        }

        Drawer drawerEntity = new Drawer(register);
        drawerRepository.save(drawerEntity);

        UserAccount user = userRepository.getReferenceById(userId);
        DrawerGroup drawerGroup = new DrawerGroup(drawerEntity, user);
        drawerGroup.AddUser(user);
        drawerGroupRepository.save(drawerGroup);

        List<String> topics = List.of(
                String.format("/notification/%s", register.getEspId())
        );
        MqttTopic topic = new MqttTopic(register.getEspId(), topics);
        topicRepository.save(topic);

        return  new DetailingDrawerDTO(drawerEntity, user);
    }
}

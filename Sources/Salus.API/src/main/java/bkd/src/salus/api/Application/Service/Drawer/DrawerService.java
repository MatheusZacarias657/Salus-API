package bkd.src.salus.api.Application.Service.Drawer;

import bkd.src.salus.api.Domain.DTO.Drawer.DetailingDrawerDTO;
import bkd.src.salus.api.Domain.DTO.Drawer.DrawerStatusDTO;
import bkd.src.salus.api.Domain.DTO.Drawer.RegisterDrawerDTO;
import bkd.src.salus.api.Domain.Entity.NoSQL.Topic.MqttTopic;
import bkd.src.salus.api.Domain.Entity.SQL.Drawer.Drawer;
import bkd.src.salus.api.Domain.Entity.SQL.Drawer.DrawerGroup;
import bkd.src.salus.api.Domain.Entity.SQL.User.UserAccount;
import bkd.src.salus.api.Domain.Exception.ValidationException;
import bkd.src.salus.api.Domain.Interface.Application.Drawer.IDrawerResumeService;
import bkd.src.salus.api.Domain.Interface.Application.Drawer.IDrawerService;
import bkd.src.salus.api.Domain.Interface.Application.RabbitMQ.IRabbitCommunicator;
import bkd.src.salus.api.Repository.NoSQL.Mongo.Topic.ITopicRepositoryMR;
import bkd.src.salus.api.Repository.SQL.Drawer.IDrawerGroupRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Drawer.IDrawerRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.Medicine.IMedicineRepositoryJPA;
import bkd.src.salus.api.Repository.SQL.User.IUserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrawerService implements IDrawerService, IDrawerResumeService {

    private final IDrawerRepositoryJPA drawerRepository;
    private final IDrawerGroupRepositoryJPA drawerGroupRepository;
    private final IUserRepositoryJPA userRepository;
    private final ITopicRepositoryMR topicRepository;
    private final IRabbitCommunicator rabbitCommunicator;
    private final IMedicineRepositoryJPA medicineRepositoryJPA;

    @Autowired
    public DrawerService(IDrawerRepositoryJPA drawerRepository, IDrawerGroupRepositoryJPA drawerGroupRepository, IUserRepositoryJPA userRepository, ITopicRepositoryMR topicRepository, IRabbitCommunicator rabbitCommunicator, IMedicineRepositoryJPA medicineRepositoryJPA) {
        this.drawerRepository = drawerRepository;
        this.drawerGroupRepository = drawerGroupRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.rabbitCommunicator = rabbitCommunicator;
        this.medicineRepositoryJPA = medicineRepositoryJPA;
    }

    @Override
    public DetailingDrawerDTO Register(RegisterDrawerDTO register, int userId){
        if (drawerRepository.findByEspId(register.getHardwareId()) != null){
            throw new ValidationException( "This drawer already exists");
        }

        Drawer drawerEntity = new Drawer(register);
        drawerRepository.save(drawerEntity);

        UserAccount user = userRepository.findById(userId).get();
        DrawerGroup drawerGroup = new DrawerGroup(drawerEntity, user);
        drawerGroup.AddUser(user);
        drawerGroupRepository.save(drawerGroup);

        List<MqttTopic> entities = List.of(
                new MqttTopic(userId, String.format("/notification/drawer/%s/request", register.getHardwareId()), register.getHardwareId()),
                new MqttTopic(userId, String.format("/notification/drawer/%s/response", register.getHardwareId()), register.getHardwareId())
        );
        topicRepository.saveAll(entities);
        List<String> topics = entities.stream().map(MqttTopic::getTopic).toList();
        rabbitCommunicator.AddSubscriber(topics);
        return  new DetailingDrawerDTO(drawerEntity, user, topics);
    }

    public List<DetailingDrawerDTO> FindByUserId(int userId){
        List<Drawer> drawers = drawerRepository.findByUserId(userId);
        UserAccount user = userRepository.getReferenceById(userId);
        List<DetailingDrawerDTO> responseDrawers = new ArrayList<>();

        for(Drawer drawer : drawers) {
            List <String> topics = List.of(
                    String.format("/notification/drawer/%s/request", drawer.getHardwareId()),
                    String.format("/notification/drawer/%s/response", drawer.getHardwareId())
            );

            responseDrawers.add(new DetailingDrawerDTO(drawer, user, topics));
        }

        return responseDrawers;
    }

    @Override
    public List<DrawerStatusDTO> CaptureStatus(int userId){
        List<DrawerStatusDTO> responses = new ArrayList<>();
        List<Drawer> drawers = drawerRepository.findByUserId(userId);

        for(Drawer drawer : drawers){
            int occupied = medicineRepositoryJPA.findDrawerUse(drawer.getHardwareId());
            responses.add(new DrawerStatusDTO((drawer.getNumberOfDrawers()-occupied), drawer.getNumberOfDrawers(), occupied));
        }
        return responses;
    }
}

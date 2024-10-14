package bkd.src.salus.api.Application.Service.Channel;

import bkd.src.salus.api.Domain.Entity.SQL.Channel.NotificationChannel;
import bkd.src.salus.api.Domain.Interface.Application.Channel.IChannelService;
import bkd.src.salus.api.Repository.SQL.Channel.IChannelRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService implements IChannelService {

    private final IChannelRepositoryJPA channelRepositoryJPA;

    @Autowired
    public ChannelService(IChannelRepositoryJPA channelRepositoryJPA) {
        this.channelRepositoryJPA = channelRepositoryJPA;
    }

    @Override
    public List<NotificationChannel> GetAllChannels(){
        return  channelRepositoryJPA.findAll();
    }
}

package bkd.src.salus.api.Domain.Interface.Application.Channel;

import bkd.src.salus.api.Domain.Entity.SQL.Channel.NotificationChannel;

import java.util.List;

public interface IChannelService {
    List<NotificationChannel> GetAllChannels();
}

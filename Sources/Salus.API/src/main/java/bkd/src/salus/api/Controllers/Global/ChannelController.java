package bkd.src.salus.api.Controllers.Global;

import bkd.src.salus.api.Domain.Interface.Application.Channel.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Channel")
public class ChannelController {

    private final IChannelService channelService;

    @Autowired
    public ChannelController(IChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("")
    public ResponseEntity ListAll(){
        return new ResponseEntity<>(channelService.GetAllChannels(), HttpStatus.OK);
    }
}

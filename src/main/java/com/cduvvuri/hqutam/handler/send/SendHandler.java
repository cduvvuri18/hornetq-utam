package com.cduvvuri.hqutam.handler.send;

import com.cduvvuri.hqutam.settings.MessageSettings;
import com.cduvvuri.hqutam.vo.PublishForm;

public interface SendHandler {
	public boolean send(PublishForm form);
}

package com.cduvvuri.hqutam.utils;

import com.cduvvuri.hqutam.enums.MessageInputType;
import com.cduvvuri.hqutam.vo.PublishForm;

public class FormUtils {
	public static String getTextMessage(PublishForm form) {
		if (form.getMessageInputType() == MessageInputType.TEXT) {
			return form.getMessageByTextarea();
		} else {
			return new String(form.getMessageInBytes());
		}
	}
}

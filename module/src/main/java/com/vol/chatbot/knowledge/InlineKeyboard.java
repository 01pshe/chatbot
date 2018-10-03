package com.vol.chatbot.knowledge;

import com.vol.chatbot.Constant;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public final class InlineKeyboard {

    public static SendMessage getKeyboard(Task task) {

        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(task.getButtonViews().size());

        for (Integer buttonView : task.getButtonViews()) {
            List<InlineKeyboardButton> listButton = new ArrayList<>(1);
            InlineKeyboardButton button = new InlineKeyboardButton()
                .setText(buttonView.toString())
                .setCallbackData(task.getUuid() + Constant.SEPARATOR + buttonView.toString());
            listButton.add(button);
            rowsInline.add(listButton);
        }

        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }
}

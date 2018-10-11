package com.vol.chatbot.keyboard;

import com.vol.chatbot.Utils;
import com.vol.chatbot.model.Question;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public final class InlineKeyboard {
    private InlineKeyboard() {
        throw new UnsupportedOperationException();
    }

    public static SendMessage getKeyboard(Question question) {
        SendMessage sendMessage = new SendMessage();
        List<String> answers = Utils.getAnswersMix(question);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(answers.size());

        for (String answer : answers) {
            List<InlineKeyboardButton> listButton = new ArrayList<>(1);
            InlineKeyboardButton button = new InlineKeyboardButton()
                .setText(answer)
                .setCallbackData(question.getId() + ";" + answer);
            listButton.add(button);
            rowsInline.add(listButton);
        }

        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        return sendMessage;

    }

}

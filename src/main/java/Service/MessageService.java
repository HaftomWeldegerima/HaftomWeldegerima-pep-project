package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessage(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        if (message != null) {
            if (messageDAO.deleteMessage(messageId)) {
                return message;
            }
        }
        return null;
    }

    public Message updateMessage(int messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
            return null;
        }
        Message message = messageDAO.getMessageById(messageId);
        if (message != null) {
            if (messageDAO.updateMessage(messageId, newMessageText)) {
                message.setMessage_text(newMessageText);
                return message;
            }
        }
        return null;
    }

    public List<Message> getMessagesByUser(int userId) {
        return messageDAO.getMessagesByUser(userId);
    }
}

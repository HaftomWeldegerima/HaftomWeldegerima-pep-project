package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);

        return app;
    }

    private void registerHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            ctx.json(createdAccount).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.login(account);
        if (loggedInAccount != null) {
            ctx.json(loggedInAccount).status(200);
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            ctx.json(createdMessage).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        } else {
            ctx.status(200);
        }
    }

    private void updateMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        String newMessageText = ctx.bodyAsClass(Message.class).getMessage_text();
        Message updatedMessage = messageService.updateMessage(messageId, newMessageText);
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesByUserHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        ctx.json(messages);
    }
}

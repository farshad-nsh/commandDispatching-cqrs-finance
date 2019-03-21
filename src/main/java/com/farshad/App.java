package com.farshad;

import com.farshad.infrastructure.commanddispatcher.CommandDispatcher;
import com.farshad.infrastructure.commanddispatcher.Invoker;
import com.farshad.infrastructure.eventdispatcher.MessageDispatcherImpl;
import com.farshad.infrastructure.listener.MessageListenerFactory;
import com.google.protobuf.Message;
import com.farshad.infrastructure.protos.generated.domain.BondProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageListenerFactory messageListenerFactory;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            System.out.println("reflection once only!");
            BondProtos.Bond.Builder asset1 = BondProtos.Bond.newBuilder().setName("farshadBond").setType("asset").setValue(5);
            Message builtMessage1=asset1.build();

            BondProtos.Bond.Builder asset2 = BondProtos.Bond.newBuilder().setName("betaBond").setType("liability").setValue(2);
            Message builtMessage2=asset2.build();


            MessageDispatcherImpl messageDispatcher=applicationContext.getBean(MessageDispatcherImpl.class);
            messageDispatcher.dispatchEvent("assets", builtMessage1);
            messageDispatcher.dispatchEvent("liabilities", builtMessage2);

            CommandDispatcher commandDispatcher=applicationContext.getBean(CommandDispatcher.class);
            commandDispatcher.dispatchCommand(builtMessage1);

        };
    }

}

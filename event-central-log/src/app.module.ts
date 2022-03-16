import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { ClientsModule, Transport } from '@nestjs/microservices';
import { AppController } from './app.controller';
import { AppService } from './app.service';

@Module({
  imports: [
    ClientsModule.register([
      {
        name: 'event-central-log',
        transport: Transport.KAFKA,
        options: {
          client: {
            clientId: 'event-central-log-client',
            brokers: ['localhost:9092'],
          },
          consumer: {
            groupId: 'event-central-log',
          },
        },
      },
    ]),
    ConfigModule.forRoot({
      isGlobal: true,
    }),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}

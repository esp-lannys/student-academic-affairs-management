import { ValidationPipe } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { HttpExceptionFilter } from './exception/exception.http';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.enableCors();
  app.useGlobalPipes(new ValidationPipe());
  app.useGlobalFilters(new HttpExceptionFilter());
  const configService: ConfigService = app.get(ConfigService);
  const port: number = configService.get<number>('PORT') || 8087;
  await app.listen(port, () => console.log('App is listening on port ' + port));
}
bootstrap();

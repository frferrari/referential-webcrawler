package com.andycot.referentialwebcrawler.impl

import com.andycot.referentialwebcrawler.api
import com.andycot.referentialwebcrawler.api.{ReferentialwebcrawlerService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
  * Implementation of the ReferentialwebcrawlerService.
  */
class ReferentialwebcrawlerServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends ReferentialwebcrawlerService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the referential-webcrawler entity for the given ID.
    val ref = persistentEntityRegistry.refFor[ReferentialwebcrawlerEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the referential-webcrawler entity for the given ID.
    val ref = persistentEntityRegistry.refFor[ReferentialwebcrawlerEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(ReferentialwebcrawlerEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[ReferentialwebcrawlerEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}

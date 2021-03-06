package com.stratio.spark.streaming.examples

import org.apache.spark.storage.StorageLevel

import scala.util.Random
import org.apache.spark.streaming.receiver._

class DummyReceiver(ratePerSec: Int) extends Receiver[Event](StorageLevel.MEMORY_ONLY) {

  def onStart() {
    // Start the thread that receives data over a connection
    new Thread("Dummy Receiver") {
      override def run() { receive() }
    }.start()
  }

  def onStop() {
    // There is nothing much to do as the thread calling receive()
    // is designed to stop by itself isStopped() returns false
  }

  /** Create a socket connection and receive data until receiver is stopped */
  private def receive() {
    while(!isStopped()) {
      store(Event(Random.nextInt(10), 2))
      Thread.sleep((1000.toDouble / ratePerSec).toInt)
    }
  }
}

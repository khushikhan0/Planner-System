package original.filehandler;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import original.model.Day;
import original.model.Event;
import original.model.EventInterface;
import original.model.Schedule;
import original.model.ScheduleInterface;
import original.model.Time;

/**
 * Handles conversion of Schedules to .xml files and vice versa.
 * Can create files in the local directory.
 */
public class XMLFileHandler implements FileHandler {
  @Override
  public void createFile(ScheduleInterface schedule) {
    try {
      Writer file = new FileWriter(schedule.getUser() + "-sched.xml");
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"" + schedule.getUser() + "\">\n");
      for (EventInterface event : schedule.getEvents()) {
        this.writeEvent(event, file);
      }
      file.write("</schedule>");
      file.close();
    } catch (IOException error) {
      throw new RuntimeException(error.getMessage());
    }
  }

  @Override
  public Schedule interpretFile(File file) {
    String user;
    ArrayList<EventInterface> events = new ArrayList<EventInterface>();

    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(file);
      xmlDoc.getDocumentElement().normalize();
      Node scheduleNode = xmlDoc.getElementsByTagName("schedule").item(0);
      if (scheduleNode == null) {
        throw new IllegalArgumentException("This file contains no schedule");
      }
      user = scheduleNode.getAttributes().getNamedItem("id").toString();
      user = user.substring(4, user.length() - 1);
      NodeList eventNodes = scheduleNode.getChildNodes();


      for (int node = 0; node < eventNodes.getLength(); node++) {
        Node currentEvent = eventNodes.item(node);
        if (currentEvent.getNodeType() == Node.ELEMENT_NODE
                && currentEvent.getNodeName().equals("event")) {
          events.add(this.makeEvent(currentEvent));
        }
      }

    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }
    return new Schedule(user, events);
  }

  private Event makeEvent(Node currentEvent) throws IOException {
    String name = null;
    String location = null;
    boolean online = false;
    Day startDay = null;
    Day endDay = null;
    Time startTime = null;
    Time endTime = null;
    List<String> invitees = new ArrayList<String>();

    if (currentEvent.getNodeType() == Node.ELEMENT_NODE) {
      NodeList details = currentEvent.getChildNodes();

      for (int node = 0; node < details.getLength(); node++) {
        Node currNode = details.item(node);
        if (currNode.getNodeType() == Node.ELEMENT_NODE) {
          String type = currNode.getNodeName();
          switch (type) {
            case "name":
              name = currNode.getTextContent();
              break;
            case "time":
              NodeList times = currNode.getChildNodes();
              for (int tIndx = 0; tIndx < times.getLength(); tIndx++) {
                if (times.item(tIndx).getNodeType() == Node.ELEMENT_NODE) {
                  type = times.item(tIndx).getNodeName();
                  switch (type) {
                    case "start-day":
                      startDay = this.stringToDay(times.item(tIndx).getTextContent());
                      break;
                    case "start":
                      startTime = this.stringToTime(times.item(tIndx).getTextContent());
                      break;
                    case "end-day":
                      endDay = this.stringToDay(times.item(tIndx).getTextContent());
                      break;
                    case "end":
                      endTime = this.stringToTime(times.item(tIndx).getTextContent());
                      break;
                    default:
                      // do nothing
                  }
                }
              }
              break;
            case "location":
              NodeList locations = currNode.getChildNodes();
              for (int lIndx = 0; lIndx < locations.getLength(); lIndx++) {
                if (locations.item(lIndx).getNodeType() == Node.ELEMENT_NODE) {
                  type = locations.item(lIndx).getNodeName();
                  if (type.equals("online")) {
                    online = Boolean.parseBoolean(locations.item(lIndx).getTextContent());
                  }
                  if (type.equals("place")) {
                    location = locations.item(lIndx).getTextContent();
                  }
                }
              }
              break;
            case "users":
              NodeList users = currNode.getChildNodes();
              for (int uIndx = 0; uIndx < users.getLength(); uIndx++) {
                if (users.item(uIndx).getNodeType() == Node.ELEMENT_NODE) {
                  String curUser = users.item(uIndx).getTextContent();
                  invitees.add(curUser);
                }
              }
              break;
            default:
              // do nothing
          }
        }
      }
    }
    return new Event(name, location, online, startDay,startTime, endDay, endTime, invitees);
  }

  private Time stringToTime(String str) {
    return new Time(Integer.parseInt(str.substring(0,2)),
            Integer.parseInt(str.substring(2)));
  }

  private Day stringToDay(String str) {
    switch (str) {
      case "Sunday":
        return Day.SUN;
      case "Monday":
        return Day.MON;
      case "Tuesday":
        return Day.TUES;
      case "Wednesday":
        return Day.WED;
      case "Thursday":
        return Day.THURS;
      case "Friday":
        return Day.FRI;
      case "Saturday":
        return Day.SAT;
      default:
        return null;
    }
  }

  private void writeEvent(EventInterface event, Writer file) throws IOException {
    file.write("\t<event>\n");
    file.write("\t\t<name>\"" + event.getAName() + "\"</name>\n");
    file.write("\t\t<time>\n");
    file.write("\t\t\t<start-day>" + event.getAStartDay().enumToString()
            + "</start-day>\n");
    file.write("\t\t\t<start>" + event.getAStartTime().timeToString()
            + "</start>\n");
    file.write("\t\t\t<end-day>" + event.getAEndDay().enumToString()
            + "</end-day>\n");
    file.write("\t\t\t<end>" + event.getAEndTime().timeToString()
            + "</end>\n");
    file.write("\t\t</time>\n");

    file.write("\t\t<location>\n");
    file.write("\t\t\t<online>" + event.getIsOnline()
            + "</online>\n");
    file.write("\t\t\t<place>\"" + event.getALocation()
            + "\"</place>\n");
    file.write("\t\t</location>\n");

    file.write("\t\t<users>\n");
    for (String user : event.getInvitees()) {
      this.writeUsers(user, file);
    }
    file.write("\t\t</users>\n");

    file.write("\t</event>\n");
  }

  private void writeUsers(String user, Writer file) throws IOException {
    file.write("\t\t\t<uid>\"" + user + "\"</uid>\n");
  }
}

/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.config.xml;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class Ip extends XMLTag {
  public InetAddress address;
  public int group;

  private String addressString;

  public Ip() {
    super("ip");
  }

  public Ip(InetAddress address, int group) {
    this();
    this.address = address;
    this.group = group;
  }

  @Override
  protected void setAttribute(String name, String value) throws SAXException {
    if (name.equals("group")) {
      group = getInt(value);
    }
  }

  @Override
  protected void finish() throws SAXException {
    try {
      address = InetAddress.getByName(addressString);
    } catch (UnknownHostException e) {
      throw new SAXException("Bad IP format: " + addressString);
    }
  }

  @Override
  protected void content(String content) {
    addressString = (addressString == null) ? content.toLowerCase() : addressString + content.toLowerCase();
  }

  @Override
  protected String saveContent() {
    return address.getHostAddress();
  }

  @Override
  protected void saveAttributes(AttributesImpl attributes) {
    addAttribute(attributes, "group", Integer.toString(group));
  }
}

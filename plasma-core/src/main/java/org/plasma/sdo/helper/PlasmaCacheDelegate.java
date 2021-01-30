package org.plasma.sdo.helper;

import java.io.InputStream;
import java.io.Serializable;

import org.plasma.runtime.ConfigurationException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PlasmaCacheDelegate<T> {
  private static String cacheFileName = "ehcache.xml";
  private CacheManager cacheManager;
  private Cache cache;

  @SuppressWarnings("unused")
  private PlasmaCacheDelegate() {
  }

  public PlasmaCacheDelegate(String cacheName) {
    InputStream is = PlasmaCacheDelegate.class.getClassLoader().getResourceAsStream(cacheFileName);
    if (is == null)
      throw new ConfigurationException("no ehcache file found: " + cacheFileName);
    this.cacheManager = CacheManager.create(is);
    if (!this.cacheManager.cacheExists(cacheName))
      this.cacheManager.addCache(cacheName);
    this.cache = this.cacheManager.getCache(cacheName);
  }

  @SuppressWarnings("unchecked")
  public T getObject(Serializable key) {
    Element elem = this.cache.get(key);
    return (elem != null) ? (T) elem.getObjectValue() : null;
  }

  public void putObject(Serializable key, T object) {
    Element elem = new Element(key, object);
    this.cache.put(elem);
  }

  @SuppressWarnings("unchecked")
  public T clearObject(Serializable key) {
    Element elem = this.cache.get(key);
    if (elem != null) {
      T object = (T) elem.getObjectValue();
      this.cache.remove(key);
      return object;
    }
    return null;
  }

}

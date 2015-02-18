/*
 * Copyright 2015 Humboldt Univerity of Berlin, INRIA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hu_berlin.german.korpling.saltnpepper.pepperModules.relannis.resolver;

import de.hu_berlin.german.korpling.saltnpepper.salt.saltCore.SLayer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Statistics used for creating resolver entries for dominance components.
 *
 * @author Thomas Krause <krauseto@hu-berlin.de>
 */
public class SpanStatistics {

  private final Set<String> layers = Collections.synchronizedSet(new HashSet<String>());

  public void addLayer(SLayer layer) {
    if(layer != null && layer.getSName() != null) {
      layers.add(layer.getSName());
    }
  }
  
  public Set<String> getLayers() {
    return new HashSet<String>(layers);
  }
}

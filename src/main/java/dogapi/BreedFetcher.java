package dogapi;

import java.util.List;

/**
 * Interface for the service of getting sub breeds of a given dog breed.
 */
public interface BreedFetcher {

    List<String> getSubBreeds(String breed) throws BreedNotFoundException;

    // NOTE: RuntimeException makes it unchecked
    class BreedNotFoundException extends RuntimeException {
        public BreedNotFoundException(String breed) {
            super("Breed not found: " + breed);
        }
    }
}
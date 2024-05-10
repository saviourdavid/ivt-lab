package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  TorpedoStore tsPrimary;
  TorpedoStore tsSecondary;
  @BeforeEach
  public void init(){
    
   tsPrimary = Mockito.mock(TorpedoStore.class);
   tsSecondary = Mockito.mock(TorpedoStore.class);
    
    this.ship = new GT4500(tsPrimary,tsSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
 
    when(tsPrimary.fire(1)).thenReturn(true);
    when(tsSecondary.fire(1)).thenReturn(true); 
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }
  

  /*
   Test firing a single torpedo from the primary torpedo store when it's not empty
   */
  @Test
  public void fireTorpedo_Single_PrimaryNotEmpty_Success(){
    // Arrange
    when(tsPrimary.isEmpty()).thenReturn(false);
    when(tsPrimary.fire(1)).thenReturn(true);
    when(tsSecondary.isEmpty()).thenReturn(true);
    
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
  }
/*
 Test firing a single torpedo from the secondary torpedo store when the primary is empty:
 */
  @Test
  public void fireTorpedo_Single_SecondaryNotEmpty_PrimaryEmpty_Success(){
    // Arrange
    when(tsPrimary.isEmpty()).thenReturn(true);
    when(tsSecondary.isEmpty()).thenReturn(false);
    when(tsSecondary.fire(1)).thenReturn(true);
    
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);
  }
/*
 Test firing a single torpedo from the primary torpedo store when both stores have torpedoes but the primary was fired last:
 */
@Test
public void fireTorpedo_ALL_NotEmpty_Success(){
  // Arrange
  when(tsPrimary.isEmpty()).thenReturn(false);
  when(tsPrimary.fire(1)).thenReturn(true);
  when(tsSecondary.isEmpty()).thenReturn(false);
  when(tsSecondary.fire(1)).thenReturn(true);
  // Act
  boolean result = ship.fireTorpedo(FiringMode.ALL);

  // Assert
  assertTrue(result);
}
/*
 Test firing a single torpedo from the secondary torpedo store when both stores have torpedoes but the secondary was fired last
 */
  @Test
  public void fireTorpedo_Single_Both_Empty(){
    // Arrange
    when(tsPrimary.isEmpty()).thenReturn(true);
    when(tsSecondary.isEmpty()).thenReturn(true);
    when(tsSecondary.fire(1)).thenReturn(false);
    when(tsPrimary.fire(1)).thenReturn(false);
    
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertFalse(result);
  }

  @Test
  public void fireTorpedo_All_Success_Both_Full(){
    // Arrange
    when(tsPrimary.isEmpty()).thenReturn(false);
    when(tsSecondary.isEmpty()).thenReturn(false);
    
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertTrue(result);
  }
  
}

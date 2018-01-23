package week5.a165036r.com.week5;

public interface Collidable
{
    String GetType();
    float GetPosX();
    float GetPosY();
    float GetRadius();

    void OnHit(Collidable _other);
}

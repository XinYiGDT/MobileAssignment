package week5.a165036r.com.week5;

import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class EntityManager
{
    public final static EntityManager Instance = new EntityManager();
    private LinkedList<EntityBase> entityList = new LinkedList<EntityBase>();
    private SurfaceView view = null;


    private EntityManager()
    {

    }

    public void init(SurfaceView _view)
    {
        //TODO
        view = _view;
    }

    public void Update(float _dt)
    {
        LinkedList<EntityBase> removalList = new LinkedList<EntityBase>();

        // Update every single entity in the list ////////////
        for (EntityBase currEntity : entityList)
        {
            // Lets check if is init, initialize if not
            if (!currEntity.IsInit())
                currEntity.Init(view);

            currEntity.Update(_dt);

            // Check if it is done
            if (currEntity.IsDone())
            {
                // Done! Time to add to the removal list
                removalList.add(currEntity);
            }
        }

        // Time to remove! ////////////////////////
        for (EntityBase currEntity : removalList)
        {
            entityList.remove(currEntity);
        }
        removalList.clear();


        //Collision Check
        for(int i =0; i <entityList.size(); ++i)
        {
            EntityBase currEntity = entityList.get(i);
            if(currEntity instanceof Collidable)
            {
                Collidable first = (Collidable) currEntity;
                for (int j = i + 1; j < entityList.size(); ++j) {
                    EntityBase otherEntity = entityList.get(j);
                    if (otherEntity instanceof Collidable) {
                        Collidable second = (Collidable) otherEntity;

                        if (Collision.SpheretoSphere(first.GetPosX(), first.GetPosY(), first.GetRadius(), second.GetPosX(), second.GetPosY(), second.GetRadius()))

                        {
                            first.OnHit(second);
                            second.OnHit(first);
                        }
                    }
                }
            }
            if(currEntity.IsDone())
                removalList.add(currEntity);
        }

        for(EntityBase currEntity : removalList)
        {
            entityList.remove(currEntity);
        }
    }

    public void Render(Canvas _canvas)
    {
        // We will use the new "rendering layer" to sort the render order
        Collections.sort(entityList, new Comparator<EntityBase>() {
            @Override
            public int compare(EntityBase o1, EntityBase o2) {
                return o1.GetRenderLayer() - o2.GetRenderLayer();
            }
        });

        // Render all entities here
        for (EntityBase currEntity : entityList)
        {
            if(currEntity.IsInit())
                currEntity.Render(_canvas);
        }
    }

    public void AddEntity(EntityBase _newEntity)
    {
        entityList.add(_newEntity);
    }

    public void UpdatePauseButton(float _dt)
    {
        for(EntityBase currEntity : entityList)
        {
            if(currEntity instanceof  SamplePauseButton)
            {
                currEntity.Update(_dt);
            }
        }
    }


    public void Clear()
    {
        entityList.clear();
    }

}

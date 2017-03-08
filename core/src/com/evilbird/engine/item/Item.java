package com.evilbird.engine.item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.evilbird.engine.common.collection.PropertySet;
import com.evilbird.engine.common.lang.Identifier;
import com.evilbird.engine.item.framework.ActorExtension;
import com.evilbird.engine.item.framework.ActorObserver;

/**
 * Instances of this class TODO:Finish
 *
 * @author Blair Butterworth
 */
public class Item implements PropertySet<ItemProperty, Object>, ActorObserver
{
    Actor delegate;
    private ItemRoot root;
    private ItemGroup parent;
    private Identifier id; //TODO Change type to string
    private Identifier type;
    private boolean selected;
    private boolean selectable;

    public Item()
    {
        this.delegate = initializeDelegate();
        this.delegate.setUserObject(this);

        setId(new Identifier("Unknown" + hashCode()));
        setType(new Identifier("Unknown"));
        setSelected(false);
        setSelectable(true);
    }

    protected Actor initializeDelegate()
    {
        return new ActorExtension(this);
    }

    public Identifier getId()
    {
        return id;
    }

    public Identifier getType()
    {
        return type;
    }

    public ItemGroup getParent()
    {
        return parent;
    }

    public ItemRoot getRoot()
    {
        return root;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public boolean getSelectable()
    {
        return selectable;
    }

    public boolean getTouchable()
    {
        return delegate.isTouchable();
    }

    public Vector2 getSize()
    {
        return new Vector2(delegate.getWidth(), delegate.getHeight());
    }

    public float getWidth()
    {
        return delegate.getWidth();
    }

    public float getHeight()
    {
        return delegate.getHeight();
    }

    public Vector2 getPosition()
    {
        return new Vector2(delegate.getX(), delegate.getY());
    }

    public float getX()
    {
        return delegate.getX();
    }

    public float getY()
    {
        return delegate.getY();
    }

    public void setId(Identifier id)
    {
        this.id = id;
    }

    public void setType(Identifier type)
    {
        this.type = type;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public void setSelectable(boolean selectable)
    {
        this.selectable = selectable;
    }

    public void setTouchable(Touchable touchable)
    {
        delegate.setTouchable(touchable);
    }

    public void setVisible(boolean visible)
    {
        delegate.setVisible(visible);
    }

    public void setSize(float width, float height)
    {
        delegate.setSize(width, height);
    }

    public void setSize(Vector2 size)
    {
        delegate.setSize(size.x, size.y);
    }

    public void setPosition(float x, float y)
    {
        delegate.setPosition(x, y);
    }

    public void setPosition(Vector2 position)
    {
        delegate.setPosition(position.x, position.y);
    }

    public void setParent(ItemGroup parent)
    {
        this.parent = parent;
    }

    public void setRoot(ItemRoot root)
    {
        this.root = root;
    }

    public void draw(Batch batch, float alpha)
    {
    }

    public void update(float delta)
    {
    }

    public void addAction(Action action)
    {
        delegate.addAction(action);
    }

    public boolean hasActions()
    {
        return delegate.hasActions();
    }

    public void clearActions()
    {
        delegate.clearActions();
    }

    public void positionChanged()
    {
    }

    public void sizeChanged()
    {
    }

    @Override
    public Object getProperty(ItemProperty key)
    {
        if (ItemProperties.Id.equals(key)){
            return getId();
        }
        else if (ItemProperties.Type.equals(key)){
            return getType();
        }
        else if (ItemProperties.Selected.equals(key)){
            return getSelected();
        }
        else if (ItemProperties.Position.equals(key)){
            return getPosition();
        }
        else if (ItemProperties.Size.equals(key)){
            return getSize();
        }
        else if (ItemProperties.Touchable.equals(key)){
            return getTouchable();
        }
        //throw new IllegalArgumentException(key.toString());
        System.out.println("Missing property: " + key.toString());
        return null; //TODO
    }

    @Override
    public void setProperty(ItemProperty key, Object value)
    {
        if (ItemProperties.Id.equals(key)){
            setId((Identifier)value);
        }
        else if (ItemProperties.Type.equals(key)){
            setType((Identifier)value);
        }
        else if (ItemProperties.Selected.equals(key)){
            setSelected((Boolean)value);
        }
        else if (ItemProperties.Position.equals(key)){
            setPosition((Vector2)value);
        }
        else if (ItemProperties.Touchable.equals(key)){
            setTouchable((Touchable)value);
        }
        else{
            throw new IllegalArgumentException(key.toString());
        }
    }
}

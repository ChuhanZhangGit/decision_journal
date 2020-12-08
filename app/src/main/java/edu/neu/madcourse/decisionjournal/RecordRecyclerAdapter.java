package edu.neu.madcourse.decisionjournal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.decisionjournal.model.Record;

public class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.RecordViewHolder> {

    private List<Record> recordList;
    private final String TAG = RecordRecyclerAdapter.class.getSimpleName();

    public RecordRecyclerAdapter() {
        this.recordList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_recycler_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {

        Record current = recordList.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void submitList(List<Record> data) {
        Log.i(TAG, String.format("submit list data size: %d", data.size()));
        if (data != null) {
            recordList.clear();
            recordList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeTextView;
        private final TextView decisionTextView;
        private final TextView emotionTextView;
        private DateFormat format = DateFormat.getTimeInstance();

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            this.timeTextView = itemView.findViewById(R.id.time_textView);
            this.decisionTextView = itemView.findViewById(R.id.decision_textView);
            this.emotionTextView = itemView.findViewById(R.id.emotion_textView);
        }

        public void bind(Record record) {
            timeTextView.setText(format.format(record.date));
            decisionTextView.setText(record.decision.toString());
            emotionTextView.setText(record.emotion.toString());
        }

    }


}
